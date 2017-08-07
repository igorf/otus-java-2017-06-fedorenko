package mock;

import com.otus.hw09.yadbe.DataSet;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity (name = "sample_data")
@Data
public class SampleDataSet extends DataSet {
    @Column(name = "another") int x;
    String y;
    @Transient boolean z;
}
