import axios from 'axios'

const API_URL = 'http://localhost:8080/api/components';

class ComponentsDataService {
    retrieveAllComponents() {
        console.log('int getAll()');
        return axios.get(API_URL);
    }

    retrieveComponentById(id) {
        console.log("in getById id = " + id);
        return axios.get(`${API_URL}/${id}`);
    }

    updateComponent(id, componentDTO) {
        return axios.put(`${API_URL}/${id}`, componentDTO);
    }

    createComponent(componentDTO) {
        return axios.post(`${API_URL}`, componentDTO);
    }
}

export default new ComponentsDataService()