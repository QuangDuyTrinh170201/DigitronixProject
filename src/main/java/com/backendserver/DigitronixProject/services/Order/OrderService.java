package com.backendserver.DigitronixProject.services.Order;

import com.backendserver.DigitronixProject.dtos.OrderDTO;
import com.backendserver.DigitronixProject.dtos.OrderDetailDTO;
import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.models.*;
import com.backendserver.DigitronixProject.repositories.*;
import com.backendserver.DigitronixProject.responses.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderResponse> getAllOrder() throws Exception {
        List<Order> orderList = orderRepository.findAll();
        return orderList.stream().map(OrderResponse::fromOrder).toList();
    }

    @Override
    public OrderResponse getOrderById(Long id) throws Exception {
        return OrderResponse.fromOrder(orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find this order in application!")));
    }

//    @Override
//    public OrderResponse createOrder(OrderDTO orderDTO) throws Exception {
//        User checkExistUser = userRepository.findById(orderDTO.getUserId())
//                .orElseThrow(() -> new DataNotFoundException("Cannot find this user in application"));
//        Customer checkExistCustomer = customerRepository.findById(orderDTO.getCustomerId())
//                .orElseThrow(() -> new DataNotFoundException("Cannot find this customer in application!"));
//        Order newOrder = new Order();
//        newOrder.setCreatedDate(orderDTO.getCreatedDate());
//        newOrder.setDeadline(orderDTO.getDeadline());
//        newOrder.setPaymentMethod(orderDTO.getPaymentMethod());
//        newOrder.setDeliveryMethod(orderDTO.getDeliveryMethod());
//        newOrder.setTotalPrice(orderDTO.getTotalPrice());
//        newOrder.setStatus(orderDTO.getStatus());
//        newOrder.setUser(checkExistUser);
//        newOrder.setCustomer(checkExistCustomer);
//        newOrder = orderRepository.save(newOrder);
//        return OrderResponse.fromOrder(newOrder);
//    }

    @Override
    public OrderResponse createOrder(OrderDTO orderDTO) throws Exception {
        // Kiểm tra và lấy thông tin người dùng và khách hàng từ cơ sở dữ liệu
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find this user in application"));
        Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find this customer in application!"));

        // Tạo một đối tượng Order từ thông tin trong OrderDTO
        Order newOrder = new Order();
        newOrder.setCreatedDate(orderDTO.getCreatedDate());
        newOrder.setDeadline(orderDTO.getDeadline());
        newOrder.setPaymentMethod(orderDTO.getPaymentMethod());
        newOrder.setDeliveryMethod(orderDTO.getDeliveryMethod());
        newOrder.setTotalPrice(orderDTO.getTotalPrice());
        newOrder.setStatus("pending");
        newOrder.setUser(user);
        newOrder.setCustomer(customer);

        // Lưu Order vào cơ sở dữ liệu
        newOrder = orderRepository.save(newOrder);

        // Tạo danh sách OrderDetail từ thông tin trong OrderDTO
        List<OrderDetailDTO> orderDetailDTOList = orderDTO.getOrderDetailDTOList();
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderDetailDTO orderDetailDTO : orderDetailDTOList) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(newOrder);

            // Lấy thông tin sản phẩm từ OrderDetailDTO
            Long productId = orderDetailDTO.getProductId();
            Long quantity = orderDetailDTO.getQuantity();

            // Kiểm tra và lấy thông tin sản phẩm từ cơ sở dữ liệu
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new DataNotFoundException("Product not found with id: " + productId));

            // Đặt thông tin cho OrderDetail
            orderDetail.setProduct(product);
            orderDetail.setQuantity(quantity);

            // Thêm OrderDetail vào danh sách
            orderDetails.add(orderDetail);
        }

        // Lưu danh sách OrderDetail vào cơ sở dữ liệu
        orderDetailRepository.saveAll(orderDetails);

        // Trả về response chứa thông tin của Order vừa được tạo
        return OrderResponse.fromOrder(newOrder);
    }


    @Override
    public OrderResponse updateOrder(Long id, OrderDTO orderDTO) throws Exception {
        Order checkExisting = orderRepository.getReferenceById(id);
        User checkExistUser = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find this user in application"));
        Customer checkExistCustomer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find this customer in application!"));
        checkExisting.setCreatedDate(orderDTO.getCreatedDate());
        checkExisting.setDeadline(orderDTO.getDeadline());
        checkExisting.setPaymentMethod(orderDTO.getPaymentMethod());
        checkExisting.setDeliveryMethod(orderDTO.getDeliveryMethod());
        checkExisting.setTotalPrice(orderDTO.getTotalPrice());
        if(Objects.equals(orderDTO.getStatus(), "confirmed")){
            List<OrderDetail> orderDetails = orderDetailRepository.findOrderDetailByOrderId(checkExisting.getId());
            for(OrderDetail orderDetail :orderDetails){
                Product product = productRepository.findById(orderDetail.getProduct().getId())
                        .orElseThrow(()->new DataNotFoundException("Cannot find this product"));
                int productQuantity = product.getQuantity();
                Long orderDetailQuantity = orderDetail.getQuantity();
                int prodQuantityAfterProduce = checkValidProductInWarehouse(productQuantity, orderDetailQuantity);
                if(prodQuantityAfterProduce < 0){
                    product.setMissing(prodQuantityAfterProduce);
                    product.setQuantity(0);
                }else {
                    product.setQuantity(prodQuantityAfterProduce);
                    product.setMissing(0);
                }
            }
        }
        checkExisting.setStatus(orderDTO.getStatus());
        checkExisting.setUser(checkExistUser);
        checkExisting.setCustomer(checkExistCustomer);
        return OrderResponse.fromOrder(orderRepository.save(checkExisting));
    }

    public int checkValidProductInWarehouse(int a, Long b){
        int quantityAfterConfirmOrder;
        quantityAfterConfirmOrder = Math.toIntExact(a - b);
        return quantityAfterConfirmOrder;
    }

    @Override
    public void deleteOrder(Long id) throws Exception {
        Order order = orderRepository.findById(id).orElseThrow(()->new DataNotFoundException("Cannot find this order to delete!"));
        orderRepository.delete(order);
    }

    @Override
    public List<OrderResponse> getOrderByCustomerId(Long id) {
        List<Order> orderList = orderRepository.findOrderByCustomerId(id);
        return orderList.stream().map(OrderResponse::fromOrder).toList();
    }

    @Override
    public List<OrderResponse> getOrderByUserId(Long id) throws Exception{
        List<Order> orderList = orderRepository.findOrderByUserId(id);
        return orderList.stream().map(OrderResponse::fromOrder).toList();
    }
}
