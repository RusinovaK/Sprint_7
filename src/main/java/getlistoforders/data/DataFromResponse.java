package getlistoforders.data;

import java.util.List;

public class DataFromResponse {
    public List<AvailableStation> getAvailableStations() {
        return availableStations;
    }

    public List<Order> getOrders() {
        return orders;
    }

    private List<Order> orders;

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    private PageInfo pageInfo;
    private List<AvailableStation> availableStations;
}
