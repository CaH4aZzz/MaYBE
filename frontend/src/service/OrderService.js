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

    createOrder(deskId, employeeId) {
        console.log("create Order ");

        return axios({
            method: 'post',
            url: `${API_URL}`,
            data: {
                deskId: deskId,
                employeeId: employeeId
            }
        });
    }

    closeOrder(orderId, deskId, employeeId) {
        console.log("update order () id " + orderId);
        return axios({
            method: 'put',
            url: `${API_URL}/${orderId}`,
            data: {
                closed: "true",
                deskId: deskId,
                employeeId: employeeId
            }
        });
    }
}


export default new OrderService()