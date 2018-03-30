package com.nealford.art.schedtapestry.component;

import com.nealford.art.schedtapestry.boundary.ScheduleDb;
import com.nealford.art.schedtapestry.entity.ScheduleItem;
import com.nealford.art.schedtapestry.page.Home;
import net.sf.tapestry.BaseComponent;
import net.sf.tapestry.contrib.table.model.ITableColumn;
import net.sf.tapestry.contrib.table.model.ITableColumnModel;
import net.sf.tapestry.contrib.table.model.ITableModel;
import net.sf.tapestry.contrib.table.model.simple.
        SimpleListTableDataModel;
import net.sf.tapestry.contrib.table.model.simple.SimpleTableColumn;
import net.sf.tapestry.contrib.table.model.simple.
        SimpleTableColumnModel;
import net.sf.tapestry.contrib.table.model.simple.SimpleTableModel;
import org.apache.log4j.Logger;

public class SchedTable extends BaseComponent {
    private ScheduleDb scheduleDb;

    public ITableModel getTableModel() {
        scheduleDb = ((Home) getPage()).getScheduleDb();
        SimpleListTableDataModel listTableDataModel =
                new SimpleListTableDataModel(scheduleDb.getList());
        return new SimpleTableModel(listTableDataModel,
                                    createColumnModel());
    }

    private ITableColumnModel createColumnModel() {
        String[] col = ScheduleDb.getColumns();
        return new SimpleTableColumnModel(new ITableColumn[] {
                new StartColumn(col[1]),
                new DurationColumn(col[2]),
                new TextColumn(col[3]),
                new EventTypeColumn(col[4])});

    }

    private class StartColumn extends SimpleTableColumn {
        public StartColumn(String colName) {
            super(colName);
        }

        public Object getColumnValue(Object row) {
            return ((ScheduleItem) row).getStart();
        }
    }


    private class DurationColumn extends SimpleTableColumn {
        public DurationColumn(String colName) {
            super(colName);
        }

        public Object getColumnValue(Object row) {
            return new Integer(((ScheduleItem) row).getDuration());
        }
    }


    private class TextColumn extends SimpleTableColumn {
        public TextColumn(String colName) {
            super(colName);
        }

        public Object getColumnValue(Object row) {
            return ((ScheduleItem) row).getText();
        }
    }


    private class EventTypeColumn extends SimpleTableColumn {
        public EventTypeColumn(String colName) {
            super(colName);
        }

        public Object getColumnValue(Object row) {
            return ((ScheduleItem) row).getEventType();
        }
    }
}