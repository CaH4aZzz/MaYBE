import axios from 'axios'

const API_URL = 'http://localhost:8080/api/statistics';

class StatisticsService {

    getSummaryReport(dateFrom, dateTo) {
        console.log("in summary report");
        return axios.get(`${API_URL}/summary/?dateFrom=` + dateFrom + `&dateTo=` + dateTo)
    }

    getComponentReport(dateFrom, dateTo) {
        console.log("in component report");
        return axios.get(`${API_URL}/component-report/?dateFrom=` + dateFrom + `&dateTo=` + dateTo)
    }

    getProductReport(dateFrom, dateTo) {
        console.log("in product report");
        return axios.get(`${API_URL}/product-report/?dateFrom=` + dateFrom + `&dateTo=` + dateTo)
    }

    getEmployeeReport(dateFrom, dateTo) {
        console.log("in employee report");
        return axios.get(`${API_URL}/employee-report/?dateFrom=` + dateFrom + `&dateTo=` + dateTo)
    }
}

export default new StatisticsService()