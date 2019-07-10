import React, {Component} from 'react';
import {ErrorMessage, Field, Form, Formik} from "formik";
import InvoiceItemService from "../service/InvoiceItemService";

class InvoiceItemsList extends Component {

    constructor(props) {
        super(props);
        console.log(this.props);
        this.state = {
            id: this.props.match.params.id,
            componentId: '',
            componentName: '',
            quantity: '',
            price: '',
            invoiceItems: []
        };
        this.refreshComponents = this.refreshComponents.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
        this.validate = this.validate.bind(this);
    }

    render() {
        let {componentId, quantity, price} = this.state;
        return (

            <div className="container">
                <p>Invoice items</p>
                <div className="container">
                    <table className="table" style={{width: '90%'}}>
                        <thead>
                        <tr>
                            <th>Component</th>
                            <th>Quantity</th>
                            <th>Price</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.invoiceItems.map(
                                invoiceItem =>
                                    <tr key={invoiceItem.id}>
                                        <td>{invoiceItem.component.name}</td>
                                        <td>{invoiceItem.quantity}</td>
                                        <td>{invoiceItem.price}</td>
                                    </tr>
                            )
                        }
                        </tbody>
                    </table>
                    <div className="container">
                        <Formik
                            initialValues={{componentId, quantity, price}}
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
                                        <ErrorMessage name="quantity" component="div"
                                                      className="alert alert-warning"/>
                                        <ErrorMessage name="price" component="div"
                                                      className="alert alert-warning"/>
                                        <fieldset className="form-group">
                                            <label>Component Name</label>
                                            <Field className="form-control" type="text" name="componentId"/>
                                        </fieldset>
                                        <fieldset className="form-group">
                                            <label>Quantity</label>
                                            <Field className="form-control" type="text" name="quantity"/>
                                        </fieldset>
                                        <fieldset className="form-group">
                                            <label>Price</label>
                                            <Field className="form-control" type="text" name="price"/>
                                        </fieldset>
                                        <button className="btn btn-success" type="submit">Save</button>
                                    </Form>
                                )
                            }
                        </Formik>
                    </div>
                    <div className="row">
                        <button className="btn btn-success" onClick={this.addComponentClicked}>Add</button>
                    </div>
                </div>
            </div>
        )
    }

    componentDidMount() {
        this.refreshComponents();
    }

    refreshComponents() {
        InvoiceItemService.getInvoiceItemsByInvoiceId(this.state.id).then(
            response => {
                console.log(response.data.content);
                this.setState({invoiceItems: response.data.content});
            }
        )
    }

    onSubmit(values) {
        let invoiceItemDTO = {
            componentId: values.componentId,
            quantity: values.quantity,
            price: values.price
        };
        console.log(invoiceItemDTO);
        console.log(this.state.id);

        InvoiceItemService.addInvoiceItemToInvoice(this.state.id, invoiceItemDTO)
            .then((response) => {
                console.log(response);
                // this.props.history.push(`/products/${this.state.id}/`)
            });
    }

    validate(values) {
        let errors = {};
        if (!values.componentId) {
            errors.componentId = 'Enter Component'
        }
        if (!values.quantity) {
            errors.quantity = 'Enter quantity of Component'
        }
        if (!values.price) {
            errors.price = 'Enter price of Component'
        }
        return errors
    }
}

export default InvoiceItemsList