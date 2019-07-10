import axios from 'axios'

const API_URL = 'http://localhost:8080/api/invoices';

class InvoiceService {

    createInvoice(invoiceDTO) {
        return axios.post(`${API_URL}`, invoiceDTO);
    }

    getInvoiceById(invoiceID) {
        return axios.get(`${API_URL}/${invoiceID}`);
    }
}

export default new InvoiceService()