package org.jbehave.web.runner.wicket.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.wicket.IClusterable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.io.IOUtils;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.StoryManager;
import org.jbehave.core.embedder.StoryManager.StoryStatus;
import org.jbehave.core.reporters.StoryReporterBuilder;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class StoryView extends Template {

    @Inject
    private Embedder embedder;

    private StoryManager storyManager;

    private StatusCache statusCache;

    protected Status selected;

    public StoryView() {
        setPageTitle("Story View");
        storyManager = embedder.storyManager();

        statusCache = new StatusCache(storyManager);
        List<IColumn<Status>> columns = new ArrayList<IColumn<Status>>();
        columns.add(new PropertyColumn<Status>(new Model<String>("Id"), "id", "id"));
        columns.add(new PropertyColumn<Status>(new Model<String>("Done"), "done", "done"));
        columns.add(new PropertyColumn<Status>(new Model<String>("Failed"), "failed", "failed"));
        columns.add(new AbstractColumn<Status>(new Model<String>("Output")) {
            public void populateItem(Item<ICellPopulator<Status>> cellItem, String componentId, IModel<Status> model) {
                cellItem.add(new ActionPanel(componentId, model));
            }
        });

        add(new StatusDataTable("table", columns));
        add(new NoMarkupMultiLineLabel("output", "", "brush: plain"));

    }

    protected String url(String path, String ext) {
        StoryReporterBuilder builder = embedder.configuration().storyReporterBuilder();
        return builder.codeLocation().toExternalForm() + "/" + StringUtils.substringBefore(path, "story") + ext;
    }

    protected void outputAs(String id, String ext) {
        String url = url(id, ext);
        File file = new File(StringUtils.substringAfter(url, "file:"));
        String content;
        try {
            content = IOUtils.toString(new FileInputStream(file));
        } catch (IOException e) {
            content = e.getMessage();
        }
        MultiLineLabel output = (MultiLineLabel) get("output");
        output.setDefaultModelObject(content);
    }

    class ActionPanel extends Panel {

        public ActionPanel(final String id, IModel<Status> model) {
            super(id, model);
            add(new Link<Status>("txt") {
                @Override
                public void onClick() {
                    Status status = (Status) getParent().getDefaultModelObject();
                    outputAs(status.getId(), this.getId());
                }
            });
        }
    }

    class StatusDataTable extends DefaultDataTable<Status> {

        public StatusDataTable(String id, List<IColumn<Status>> columns) {
            super(id, columns, new SortableStatusDataProvider(), 10);
        }

    }

    class SortableStatusDataProvider extends SortableDataProvider<Status> {
        public SortableStatusDataProvider() {
            // set default sort
            setSort("id", SortOrder.ASCENDING);
        }

        public Iterator<? extends Status> iterator(int first, int count) {
            return statusCache.find(first, count, getSort()).iterator();
        }

        public int size() {
            return statusCache.getCount();
        }

        public IModel<Status> model(Status object) {
            return new DetachableStatusModel(object);
        }

    }

    class DetachableStatusModel extends LoadableDetachableModel<Status> {
        private final String id;

        public DetachableStatusModel(Status s) {
            this(s.getId());
        }

        public DetachableStatusModel(String id) {
            this.id = id;
        }

        @Override
        protected Status load() {
            return statusCache.get(id);
        }

        @Override
        public int hashCode() {
            return Long.valueOf(id).hashCode();
        }

        @Override
        public boolean equals(final Object obj) {
            return EqualsBuilder.reflectionEquals(this, obj);
        }

    }

    class StatusCache {
        private final Map<String, Status> map = Collections.synchronizedMap(new HashMap<String, Status>());
        private final StoryManager storyManager;

        public StatusCache(StoryManager storyManager) {
            this.storyManager = storyManager;
            reload();
        }

        public void reload() {
            for (StoryStatus status : storyManager.list()) {
                add(new Status(status));
            }
        }

        public Status get(String id) {
            Status o = map.get(id);
            if (o == null) {
                throw new RuntimeException("Status " + id + "not found");
            }
            return o;
        }

        public void add(final Status status) {
            String id = status.getId();
            if (!map.containsKey(id)) {
                map.put(id, status);
            }
        }

        public List<Status> find(long first, long count, SortParam sort) {
            reload();
            return getIndex(sort).subList((int) first, (int) (first + count));
        }

        protected List<Status> getIndex(SortParam sort) {
            final int multiplier = (sort.isAscending() ? 1 : -1);
            List<Status> index = new ArrayList<StoryView.Status>(map.values());
            if (sort == null || sort.getProperty().equals("id")) {
                Collections.sort(index, new Comparator<Status>() {
                    public int compare(Status arg0, Status arg1) {
                        return multiplier * arg0.getId().compareTo(arg1.getId());
                    }
                });
            } else if (sort.getProperty().equals("done")) {
                Collections.sort(index, new Comparator<Status>() {
                    public int compare(Status arg0, Status arg1) {
                        return multiplier * arg0.isDone().compareTo(arg1.isDone());
                    }
                });
            } else if (sort.getProperty().equals("failed")) {
                Collections.sort(index, new Comparator<Status>() {
                    public int compare(Status arg0, Status arg1) {
                        return multiplier * arg0.isFailed().compareTo(arg1.isFailed());
                    }
                });
            }
            return index;
        }

        public int getCount() {
            return map.size();
        }

        public void delete(final Status contact) {
            map.remove(contact.getId());
        }

    }

    class Status implements IClusterable {
        private String id;

        private boolean done;

        private boolean failed;

        public Status(StoryStatus status) {
            this.id = status.getPath();
            this.done = status.isDone();
            this.failed = status.isFailed();
        }

        public String getId() {
            return id;
        }

        public Boolean isDone() {
            return done;
        }

        public Boolean isFailed() {
            return failed;
        }

        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }

        public boolean equals(Object obj) {
            return EqualsBuilder.reflectionEquals(this, obj);
        }

    }

}
