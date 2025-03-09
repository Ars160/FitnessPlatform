import React, { useState } from "react";
import { addProduct } from "../../api";
import { Link, useLoaderData, useNavigate } from "react-router-dom";

export default function CreateProduct() {
    const navigate = useNavigate();
    const categories = useLoaderData();

    const [product, setProduct] = useState({
        name: "",
        price: 0,
        quantity: 0,
        category: "",
        image: null
    });

    const handleName = (ev) => {
        setProduct({ ...product, name: ev.target.value });
    };
    const handlePrice = (ev) => {
        setProduct({ ...product, price: parseInt(ev.target.value) });
    };
    const handleQuantity = (ev) => {
        setProduct({ ...product, quantity: parseInt(ev.target.value) });
    };
    const handleCategory = (ev) => {
        const categoryId = ev.target.value;
        const selectedCategory = categories.find(cat => cat.id == categoryId);
        setProduct({ ...product, category: selectedCategory });
    };
    
    const handleImage = (ev) => {
        setProduct({ ...product, image: ev.target.files[0] });
    };

    const handleSubmit = async (ev) => {
        ev.preventDefault();
        const formDataToSend = new FormData();
        formDataToSend.append("name", product.name);
        formDataToSend.append("price", product.price);
        formDataToSend.append("quantity", product.quantity);
        formDataToSend.append("categoryId", product.category.id);
        formDataToSend.append("image", product.image);
        const savedProduct = await addProduct(formDataToSend);
        navigate("/products", {
            state: { message: savedProduct.name + " saved"}
        });
    };

    return (
        <div className="container">
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label htmlFor="name" className="form-label">Name:</label>
                    <input
                        type="text"
                        className="form-control"
                        id="name"
                        value={product.name}
                        onChange={handleName}
                    />
                </div>
                <div className="mb-3">
                    <label htmlFor="price" className="form-label">Price:</label>
                    <input
                        type="number"
                        className="form-control"
                        id="price"
                        value={product.price}
                        onChange={handlePrice}
                    />
                </div>
                <div className="mb-3">
                    <label htmlFor="quantity" className="form-label">Quantity:</label>
                    <input
                        type="number"
                        className="form-control"
                        id="quantity"
                        value={product.quantity}
                        onChange={handleQuantity}
                    />
                </div>
                <div className="mb-3">
                    <label htmlFor="category_id" className="form-label">Category:</label>
                    <select
                        className="form-select"
                        id="category_id"
                        onChange={handleCategory}
                        value={product.category ? product.category.id : ''}
                    >
                        <option value="">Categories</option>
                        {categories.map((cat) => (
                            <option key={cat.id} value={cat.id}>
                                {cat.name}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="mb-3">
                    <label htmlFor="image" className="form-label">Image:</label>
                    <input
                        type="file"
                        className="form-control"
                        id="image"
                        accept="image/*"
                        onChange={handleImage}
                    />
                </div>
                <Link to="/products" className="btn btn-secondary">Back</Link>
                <button type="submit" className="btn btn-primary">Add Product</button>
            </form>
            
        </div>
    );
}
