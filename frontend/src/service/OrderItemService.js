import axios from 'axios'

const API_URL = 'http://localhost:8080/api/orders';

class OrderItemService {

    getOrderItemsByOrderId(id) {
        console.log('in getAllOrders()');
        return axios.get(`${API_URL}/${id}`);
    }

    getOrderItemsById(id) {
        console.log("in getOrderItemsById id = " + id);
        return axios.get(`${API_URL}/${id}/orderItems`);
    }

    addOrderItemToOrder(orderId, price, productId, quantity) {

        return axios({
            method: 'post',
            url: `${API_URL}/${orderId}/orderItems`,
            data: {
                orderId: orderId,
                price: price,
                productId: productId,
                quantity: quantity
            }
        });
    }
}

export default new OrderItemService()