package com.shawn.sys.dialect;

import com.shawn.sys.dialect.Schema.Tables;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by wanglu-jf on 17/9/6.
 */
@MappedSuperclass
public class AbstractUserAuth implements Serializable {

    private static final long serialVersionUID = -1661998966159193712L;

    private static final String SEQUENCE = Tables.USERAUTH + Schema.SEQ_SUFFIX;

//    @SequenceGenerator(name = SEQUENCE, sequenceName = SEQUENCE)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE)
//    @Id
    @GeneratedValue
    @Id
    @Column(name = "ID",length = 40,unique = true,nullable = false)
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
