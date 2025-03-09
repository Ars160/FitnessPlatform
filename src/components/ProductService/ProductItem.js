import { useEffect, useState } from "react";
import { Link,useLoaderData, useNavigate } from "react-router-dom";
import { getOneCategory, removeProduct,buyProduct } from "../../api";
import './ProductItem.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { jwtDecode } from "jwt-decode";

export default function ProductItem(){

    const product = useLoaderData();
    const navigate = useNavigate();
    const [category, setCategory] = useState({
        id: '',
        name: ''
    });
    const decoded = jwtDecode(JSON.parse(localStorage.getItem("token")));
    

    useEffect(() => {
        const ProductCat = async () => {
            const CategoryData = await getOneCategory(product.category.id);
            setCategory(CategoryData);
        }; ProductCat() 
    }, [])

    const  handleClick = async () => {
        const confirmDelete = window.confirm('Are you sure you want to delete this entry?');
         if(confirmDelete){
        const response = await removeProduct(product.id);
        navigate("/products", { state: {message: product.name + " deleted"} });
    }
    }

    const handleBuy = async () => {
        const response = await buyProduct(product.id);
        navigate("/products", { state: {message: product.name + " buied"}})
    }

    return (
        <div className="container mt-5 profile-container">
            <h1 className="mb-4">{product.name}</h1>
            <div className="row">
            <div className="col-md-6">
                    <div className="d-flex justify-content-center">
                        <img src={`data:image/jpeg;base64,${product.productImage}`} className="img-fluid p_item" alt={product.name} />
                    </div>
                </div>
                <div className="col-md-6">
                    <div className="mb-2 ">
                    <p className="oldprice d-inline"><del>{product.price + 125} ₸</del></p>&nbsp;&nbsp;
                    <p className="d-inline"><strong>{product.price} ₸</strong></p>
                    </div>
                    <p><strong>Quantity:</strong> {product.quantity}</p>
                    <p><strong>Category:</strong> {category.name}</p>
                    <div className="mt-4">
                    <button onClick={handleBuy} className="btn btn-success me-2">Buy</button>
                    {decoded.role === 'ROLE_ADMIN' && 
                        <>                       
                            <Link to={'/products/' + product.id + '/edit'} className="btn btn-primary me-2">Edit</Link>
                            <button onClick={handleClick} className="btn btn-danger me-2">Delete</button>
                        </>
                    }
                    <Link to="/products" className="btn btn-secondary">Back</Link>
                    </div>
                </div>
            </div>
        </div>
    );
    
}