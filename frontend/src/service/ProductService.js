import axios from 'axios'

const API_URL = 'http://localhost:8080/api/products';

class ProductService {

    getAllProducts() {
        return axios.get(API_URL);
    }

}

export default new ProductService()