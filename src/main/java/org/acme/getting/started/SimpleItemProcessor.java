package org.acme.getting.started;

import javax.batch.api.chunk.ItemProcessor;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import java.time.LocalDate;

@Dependent
@Named
public class SimpleItemProcessor implements ItemProcessor {
    @Override
    public Object processItem(Object item) {
        return ((Integer) item).intValue() % 2 == 0 ? null : ((Integer) item).intValue();
    }
}
