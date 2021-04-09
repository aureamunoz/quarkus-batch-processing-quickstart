package org.acme.getting.started;

import javax.batch.api.chunk.ItemReader;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.StringTokenizer;

@Dependent
@Named
@Transactional
public class SimpleItemReader implements ItemReader {
    private StringTokenizer tokens;
    private Integer count=0;


    @Override
    public void open(Serializable checkpoint) {
        tokens = new StringTokenizer("1,2,3,4,5,6,7,8,9,10", ",");
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public Object readItem() {
        if (tokens.hasMoreTokens()) {
            this.count++;
            String tempTokenize = tokens.nextToken();
//            jobContext.setTransientUserData(count);
            return Integer.valueOf(tempTokenize);
        }
        return null;
    }

    @Override
    public Serializable checkpointInfo() {
        return null;
    }

}
