import axios from 'axios'

const API_URL = 'http://localhost:8080/api/components'

class ComponentsDataService {
    retrieveAllComponents(){
        console.log('int getAll()')
        return axios.get(API_URL);
    }

    retrieveComponentById(id){
        console.log("in getById")
        // eslint-disable-next-line no-template-curly-in-string
        return axios.get(API_URL);
    }

    updateComponentById(id){
        return axios.put('http://localhost:8080/api/components/{id}');
    }
}
export default new ComponentsDataService()