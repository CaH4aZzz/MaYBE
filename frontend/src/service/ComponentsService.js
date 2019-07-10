import axios from 'axios'

const API_URL = 'http://localhost:8080/api/components';

class ComponentsService {
    retrieveAllComponents() {
        return axios.get(API_URL);
    }

    retrieveComponentById(id) {
        return axios.get(`${API_URL}/${id}`);
    }

    updateComponent(id, componentDTO) {
        return axios.put(`${API_URL}/${id}`, componentDTO);
    }

    createComponent(componentDTO) {
        return axios.post(`${API_URL}`, componentDTO);
    }
}

export default new ComponentsService()