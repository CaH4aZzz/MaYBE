import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import {createStore} from "redux";

ReactDOM.render(<App />, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();

const initialState = {
    result: 1,
    lastValues: []
};

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case "ADD":
            state.result += action.payload;
            break;
        case "SUBTRACT":
            state.result -= action.payload;
            break;
        default: break;
    }
    return state;
};

const store = createStore(reducer);

store.subscribe(() => {
    console.log("Store updated", store.getState());
});

store.dispatch({
    type: "ADD",
    payload: 100
});

store.dispatch({
    type: "SUBTRACT",
    payload: 33
});