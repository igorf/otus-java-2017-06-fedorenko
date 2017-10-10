package ru.otus.lib.channel;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.otus.lib.app.Msg;
import ru.otus.lib.app.MsgClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketMsgClient implements MsgClient {
    private static final Logger logger = Logger.getLogger(SocketMsgClient.class.getName());
    private static final int WORKERS_COUNT = 2;

    private final BlockingQueue<Msg> output = new LinkedBlockingQueue<>();
    private final BlockingQueue<Msg> input = new LinkedBlockingQueue<>();

    private final ExecutorService executor;
    private final Socket socket;

    public SocketMsgClient(Socket socket) {
        this.socket = socket;
        this.executor = Executors.newFixedThreadPool(WORKERS_COUNT);
    }

    @Override
    public void send(Msg msg) {
        output.add(msg);
    }

    @Override
    public Msg pool() {
        return input.poll();
    }

    @Override
    public Msg take() throws InterruptedException {
        return input.take();
    }

    @Override
    public void close() throws IOException {
        executor.shutdown();
    }

    public void init() {
        executor.execute(this::sendMessage);
        executor.execute(this::receiveMessage);
    }

    private void receiveMessage() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine);
                if (inputLine.isEmpty()) {
                    String json = stringBuilder.toString();
                    Msg msg = getMsgFromJSON(json);
                    if (msg != null) {
                        input.add(msg);
                    }
                    stringBuilder = new StringBuilder();
                }
            }
        } catch (IOException | ParseException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            while (socket.isConnected()) {
                Msg msg = output.take();
                String json = new ObjectMapper().writeValueAsString(msg);
                out.println(json);
                out.println();
            }
        } catch (InterruptedException | IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    private static Msg getMsgFromJSON(String json) throws ParseException, ClassNotFoundException, IOException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
        String className = (String) jsonObject.get(Msg.CLASS_NAME_VARIABLE);
        Class<?> msgClass = Class.forName(className);
        return (Msg) new ObjectMapper().readValue(json, msgClass);
    }
}
