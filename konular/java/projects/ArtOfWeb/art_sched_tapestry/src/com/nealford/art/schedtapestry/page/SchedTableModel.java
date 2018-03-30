package com.nealford.art.schedtapestry.page;

import java.util.Arrays;
import java.util.Iterator;

import com.nealford.art.schedtapestry.boundary.ScheduleDb;
import net.sf.tapestry.contrib.table.model.ITableColumnModel;
import net.sf.tapestry.contrib.table.model.ITableModel;
import net.sf.tapestry.contrib.table.model.ITablePagingState;
import net.sf.tapestry.contrib.table.model.ITableSortingState;
import net.sf.tapestry.contrib.table.model.simple.SimpleTableColumnModel;

public class SchedTableModel implements ITableModel {
    public SchedTableModel() {
    }
    public ITableColumnModel getColumnModel() {
        return new SimpleTableColumnModel(
                Arrays.asList(ScheduleDb.getColumns()));
    }
    public ITableSortingState getSortingState() {
        return null;
    }
    public ITablePagingState getPagingState() {
        return null;
    }
    public int getPageCount() {
        return 1;
    }
    public Iterator getCurrentPageRows() {
        return null;
    }

}