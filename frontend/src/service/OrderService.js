import axios from 'axios'

const API_URL = 'http://localhost:8080/api/orders';

class OrderService {
    getAllOrders() {
        console.log('in getAllOrders()');
        return axios.get(API_URL);
    }

    getOrderById(id) {
        console.log("in getOrderItemsById id = " + id);
        return axios.get(`${API_URL}/${id}`);
    }
}

export default new OrderService()