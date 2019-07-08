import axios from 'axios';

const API_URL = 'http://localhost:8080/api/desks';

class DeskService {

    getAllDesks() {
        console.log("in getAllDesks()");
        return axios({
            method: 'get',
            url: API_URL,
            params: {
                state: 'AVAILABLE'
            }
        });
    }
}

export default new DeskService()