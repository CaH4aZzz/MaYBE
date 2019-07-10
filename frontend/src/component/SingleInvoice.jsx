import React, {Component} from 'react';
import {ErrorMessage, Field, Form, Formik} from "formik";
import InvoiceService from "../service/InvoiceService";

class SingleInvoice extends Component {

    constructor(props) {
        super(props);
        console.log('new invoice');
        console.log(this.props.match.params.id);
        this.state = {
            id: this.props.match.params.id,
            name: '',
            invoiceTypeId: '',
            employeeId: ''
        };
        this.onSubmit = this.onSubmit.bind(this);
        this.validate = this.validate.bind(this);
    }

    render() {
        let {name, invoiceTypeId} = this.state;

        return (
            <div>
                <div className="flex">
                    <h3>Invoice</h3>
                </div>
                <div className="container">
                    <Formik
                        initialValues={{name, invoiceTypeId}}
                        onSubmit={this.onSubmit}
                        validateOnChange={false}
                        validateOnBlur={false}
                        validate={this.validate}
                        enableReinitialize={true}>
                        {
                            (props) => (
                                <Form>
                                    <ErrorMessage name="name" component="div"
                                                  className="alert alert-warning"/>
                                    <ErrorMessage name="invoiceTypeId" component="div"
                                                  className="alert alert-warning"/>
                                    <fieldset className="form-group">
                                        <label>Name</label>
                                        <Field className="form-control" type="text" name="name"/>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>InvoiceType</label>
                                        <Field className="form-control" type="text" name="invoiceTypeId"/>
                                    </fieldset>
                                    <button className="btn btn-success" type="submit">Save</button>
                                </Form>
                            )
                        }
                    </Formik>
                </div>
            </div>
        )
    }

    componentDidMount() {
        console.log('in single Invoice' + this.state.id);

        if (this.state.id == -1) {
            console.log("id - 1?");
            return
        }

        InvoiceService.getInvoiceById(this.state.id)
            .then(response => {
                    this.setState({
                        id: response.data.id,
                        name: response.data.name,
                        invoiceTypeId: response.data.invoiceTypeId,
                        employeeId: response.data.employeeId
                    })
                }
            )
    }

    async onSubmit(values) {
        let invoiceDTO = {
            name: values.name,
            invoiceTypeId: values.invoiceTypeId,
            employeeId: 1
        };
        console.log(invoiceDTO);

        const response = await InvoiceService.createInvoice(invoiceDTO);
        console.log("new invoice ", response.data.id);
        this.props.history.push(`/invoices/${response.data.id}/invoiceItmes`);
    }

    validate(values) {
        let errors = {};
        if (!values.name) {
            errors.name = 'Enter Product name'
        }
        if (!values.invoiceTypeId) {
            errors.invoiceTypeId = 'Enter type of Invoice'
        }
        return errors
    }
}

export default SingleInvoice