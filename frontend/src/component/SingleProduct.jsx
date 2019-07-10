import React, {Component} from 'react';
import {ErrorMessage, Field, Form, Formik} from "formik";
import ProductService from "../service/ProductService";

class SingleProduct extends Component {

    constructor(props) {
        super(props);
        console.log('single');
        this.state = {
            id: this.props.match.params.id,
            name: '',
            price: ''
        };
        this.onSubmit = this.onSubmit.bind(this);
        this.validate = this.validate.bind(this);
    }

    render() {
        let {name, price} = this.state;

        return (
            <div>
                <div className="flex">
                    <h3>Product</h3>
                    <button className="btn btn-warning" onClick={() => this.goToRecipe()}>Recipe</button>
                </div>
                <div className="container">
                    <Formik
                        initialValues={{name, price}}
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
                                    <ErrorMessage name="price" component="div"
                                                  className="alert alert-warning"/>
                                    <fieldset className="form-group">
                                        <label>Name</label>
                                        <Field className="form-control" type="text" name="name"/>
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
            </div>
        )
    }

    componentDidMount() {
        console.log('in singleProd' + this.state.id);

        if (this.state.id == -1) {
            console.log("id - 1?");
            return
        }

        ProductService.getProductById(this.state.id)
            .then(response => {
                    this.setState({
                        id: response.data.id,
                        name: response.data.name,
                        price: response.data.price
                    })
                }
            )
    }

    onSubmit(values) {
        let productDTO = {
            name: values.name,
            price: values.price
        };
        console.log(productDTO);

        if (this.state.id == -1) {
            ProductService.createProduct(productDTO)
                .then(() => this.props.history.push('/products'));
            console.log('inside if => POST')
        } else {
            ProductService.updateProduct(this.state.id, productDTO)
                .then(() => this.props.history.push('/products'));
            console.log('inside else => PUT');
        }
    }

    validate(values) {
        let errors = {};
        if (!values.name) {
            errors.name = 'Enter Product name'
        }
        if (!values.price) {
            errors.price = 'Enter price of Product'
        }
        return errors
    }

    goToRecipe() {
        console.log("in single prop got to recipe ", this.state.id);
        this.props.history.push(`/products/${this.state.id}/components`);
    }

}

export default SingleProduct