package com.nealford.art.history.servletemotherearth.lib;

import java.sql.*;
import java.text.NumberFormat;

public class HtmlSQLResult {
    private String sql;
    private Connection con;
    private boolean shoppingForm;

    public HtmlSQLResult(String sql, Connection con) {
        this.sql = sql;
        this.con = con;
    }

    /**
     * The <code>toString()</code> method returns a
     * <code>java.sql.ResultSet</code> formatted as an HTML table.
     *
     * NB: This should be called at most once for a given set of output!
     * @return <code>String</code> formatted as an HTML table containing
     * all the elements of the result set
     */
    public String toString() {
        StringBuffer out = new StringBuffer();
        try {
            Statement stmt = con.createStatement();
            stmt.execute(sql);
            ResultSet rs = stmt.getResultSet();
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();

            setupTable(out);
            generateHeaders(out, rsmd, numCols);

            while (rs.next()) {
                generateStandardRow(rs, rsmd, numCols, out);
                generateShoppingForm(out, rs.getInt("id"));
                endRow(out);
            }
            endTable(out);
        } catch (SQLException e) {
            out.append("</TABLE><H1>ERROR:</H1> " + e.getMessage());
        }

        return out.toString();
    }

    private void endTable(StringBuffer out) {
        out.append("</TABLE>\n");
    }

    private void endRow(StringBuffer out) {
        out.append("</TR>\n");
    }

    private void generateShoppingForm(StringBuffer b,
            int currentId) {
        if (shoppingForm) {
            b.append("<TD>");
            b.append("<form action='ShowCart' method='post'>");
            b.append("Qty: <input type='text' size='3' " +
                     "name='quantity'>");
            b.append("<input type='hidden' name='id' " +
                     "value='" + currentId + "'>");
            b.append("<input type='submit' name='submit' " +
                     "value='Add to cart'>");
            b.append("</form>");
        }
    }

    private void generateStandardRow(ResultSet rs,
                                     ResultSetMetaData rsmd,
                                     int numCols,
                                     StringBuffer out)
            throws SQLException {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        out.append("<TR>");
        for (int i = 1; i <= numCols; i++) {
            Object obj = rs.getObject(i);
            if (obj != null &&
                    rsmd.getColumnType(i) == java.sql.Types.DOUBLE)
                out.append("<TD align='right'> " +
                formatter.format(rs.getDouble(i)));
            else if (obj == null)
                out.append("<TD>&nbsp;");
            else
                out.append("<TD>" + obj.toString());
        }

    }

    private void generateHeaders(StringBuffer out,
                                 ResultSetMetaData rsmd,
                                 int numcols)
            throws SQLException {
        for (int i = 1; i <= numcols; i++) {
            out.append("<TH>");
            out.append(rsmd.getColumnLabel(i));
        }
        if (shoppingForm) {
            out.append("<TH>" + "Buy");
        }
        out.append("</TR>\n");
    }

    private void setupTable(StringBuffer out) {
        out.append("<TABLE border=1>\n");
        out.append("<TR>");
    }

    public boolean isShoppingForm() {
        return shoppingForm;
    }

    public void setShoppingForm(boolean value) {
        shoppingForm = value;
    }
}
