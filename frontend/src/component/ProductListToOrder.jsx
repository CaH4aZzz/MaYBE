// @import url(https://unpkg.com/bootstrap@4.1.0/dist/css/bootstrap.min.css);
import React, {Component} from 'react';
import ProductService from "../service/ProductService";
import OrderItemService from "../service/OrderItemService";
import Table from "react-bootstrap/Table";

class ProductListToOrder extends Component {

    constructor(props) {
        super(props);
        this.state = {
            productsNum: '',
            productBeingAdded: null,
            products: []
        };
        this.refreshProducts = this.refreshProducts.bind(this);
        this.addProductToOrder = this.addProductToOrder.bind(this);
    }

    componentDidMount() {
        console.log(this.props.myData);
        this.refreshProducts();
    }

    refreshProducts() {
        ProductService.getAllProducts().then(
            response => {
                this.setState({products: response.data.content});
            }
        )
    }

    addProductToOrder(id) {
        this.setState({
            productBeingAdded: id
        })
    }

    handleInputChange = (evt) => {
        this.setState({
            productsNum: evt.target.value
        })
    };

    addProdToOrderSubmit = (price) => {

        const productsNum = parseInt(this.state.productsNum, 10);

        const isValid = productsNum && productsNum > 0 && productsNum % 1 === 0;

        if (!isValid) {
            alert('Please enter positive integer');
            return;
        }

        const productId = this.state.productBeingAdded;
        const orderId = this.props.match.params.id;

        const response = OrderItemService.addOrderItemToOrder(orderId, price, productId, productsNum);
        console.log("response = ", response);
        this.refreshProducts();
    };

    render() {
        return (
            <div className="container">
                <p>All Products</p>
                <div className="container scroll">
                    <Table striped bordered hover>
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Price</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.products.map(
                                product =>
                                    <tr key={product.id}>
                                        <td>{product.name}</td>
                                        <td>{product.price}</td>
                                        <td>
                                            {this.state.productBeingAdded !== product.id ? (
                                                <button className="btn btn-success"
                                                        onClick={() => this.addProductToOrder(product.id)}>Add to order
                                                </button>
                                            ) : <><input onChange={this.handleInputChange}/>
                                                <button className="btn btn-success"
                                                        onClick={() => this.addProdToOrderSubmit(product.price)}>Add
                                                </button>
                                            </>}
                                        </td>
                                    </tr>
                            )
                        }
                        </tbody>
                    </Table>
                </div>
            </div>
        )
    }
}

export default ProductListToOrder

// export default withRouter(ProductList)