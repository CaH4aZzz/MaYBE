import axios from 'axios'

const API_URL = 'http://localhost:8080/api/products';

class ProductService {

    getAllProducts() {
        console.log("service prod : get all");
        return axios.get(API_URL);
    }

    getProductById(id) {
        return axios.get(`${API_URL}/${id}`);
    }

    createProduct(productDTO) {
        return axios.post(`${API_URL}`, productDTO);
    }

    updateProduct(id, productDTO) {
        return axios.put(`${API_URL}/${id}`, productDTO);
    }
}

export default new ProductService()