package lib;

public class Pager {

    protected Integer currentPage = 1;
    protected Integer recordCount;
    protected Integer totalPages;
    protected Integer offset = 0;
    protected Integer rows = 10;

    public static final Integer PAGE_SIZE = 25;

    public Pager(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public void setRecordCount(Integer recordCount) {
        this.recordCount = recordCount;
    }

    public void resolvePager() {
        setTotalPages();
        setOffset();
        setRows();
    }

    private void setTotalPages() {
        this.totalPages = (int) Math.ceil((double) recordCount / (double) PAGE_SIZE);
    }

    private void setOffset() {
        this.offset = (currentPage - 1) * PAGE_SIZE;
    }

    private void setRows() {
        this.rows = PAGE_SIZE;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Integer getRecordCount() {
        return recordCount;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getOffset() {
        return offset;
    }

    public Integer getRows() {
        return rows;
    }

    public Integer getPageSize() {
        return PAGE_SIZE;
    }
}
