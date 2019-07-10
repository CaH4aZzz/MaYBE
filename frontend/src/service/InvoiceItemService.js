import axios from 'axios'

const API_URL = 'http://localhost:8080/api/invoices';

class InvoiceItemService {

    getInvoiceItemsByInvoiceId(invoiceId) {
        console.log("prod invItem service invoiceId = ", invoiceId);
        return axios.get(`${API_URL}/${invoiceId}/invoiceItems`);
    }

    addInvoiceItemToInvoice(invoiceId, invoiceItemDTO) {
        console.log("prod comp service prodId = ", invoiceId, invoiceItemDTO);
        return axios.post(`${API_URL}/${invoiceId}/invoiceItems`, invoiceItemDTO);
    }
}

export default new InvoiceItemService()