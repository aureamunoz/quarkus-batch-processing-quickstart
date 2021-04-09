package org.acme.getting.started;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Dependent
@Named
public class SimpleItemWriter extends AbstractItemWriter {

    @Override
    public void writeItems(List<Object> items) {
        items.stream().forEach(System.out::println);
    }
}
