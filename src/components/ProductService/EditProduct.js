import { useEffect, useState } from "react";
import {updateProduct, getOneProduct} from "../../api";
import { useLoaderData, useNavigate, useParams } from "react-router-dom";

export default function EditCar(){

    const params = useParams();
    const categories = useLoaderData();

    useEffect(() => {
        const getProduct = async ()=>{
            const response = await getOneProduct(params.product_id);
            setProduct(response)
        }
        getProduct()
    }, [])

    const navigate = useNavigate();

    const  [product, setProduct] = useState({
        name: '',
        price: 0,
        quantity: 0,
        category: '',
        image: null
    });

    const handleName = (ev) => {setProduct({...product, name:ev.target.value});}
    const handlePrice = (ev) => {setProduct({...product, price: parseInt(ev.target.value)});}
    const handleQuantity = (ev) => {setProduct({...product, quantity: parseInt(ev.target.value)});}
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
        formDataToSend.append("id",product.id);
        formDataToSend.append("name", product.name);
        formDataToSend.append("price", product.price);
        formDataToSend.append("quantity", product.quantity);
        formDataToSend.append("categoryId", product.category.id);
        formDataToSend.append("image", product.image);
        const UpdatedProduct = await updateProduct(formDataToSend);
        navigate("/products", { state: {message: product.name + " edited"} });
    }


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
                    <label htmlFor="cat_id" className="form-label">Category:</label>
                    <select
                        className="form-select"
                        id="cat_id"
                        onChange={handleCategory}
                        value={product.category.id}
                    >
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
                <button type="submit" className="btn btn-primary">Edit Product</button>
            </form>
        </div>
    );
}