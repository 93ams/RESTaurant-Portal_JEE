package astielau.restaurantportal.dto;

import java.util.List;

public class PurchaseDTO {
    
    private String client;
    private Long id;
    private Double total;
    private List<OrderItemDTO> items;

    public PurchaseDTO() {}
    public PurchaseDTO(String client, Long id, Double total, List<OrderItemDTO> items) {
        this.client = client;
        this.id = id;
        this.total = total;
        this.items = items;
    }
    
    public String getClient() { return client; } 
    public void setClient(String client) { this.client = client; }

    public Long getId() { return id; } 
    public void setId(Long id) { this.id = id; }

    public Double getTotal() { return total; } 
    public void setTotal(Double total) { this.total = total; }

    public List<OrderItemDTO> getItems() { return items; } 
    public void setItems(List<OrderItemDTO> items) { this.items = items; }
    public void addItem(OrderItemDTO item) { this.items.add(item); }
    
}
