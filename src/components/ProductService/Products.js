import { Link, useLocation, useNavigate, useParams } from "react-router-dom";
import 'react-notifications/lib/notifications.css';
import { useEffect, useState } from "react";
import { getCategoryandProducts } from "../../api";
import './Products.css';
import 'bootstrap/dist/css/bootstrap.css';
import { jwtDecode } from "jwt-decode";
export default function Products(){

    const location = useLocation();
    const params = useParams();
    const navigate = useNavigate();
    const [products, setProducts] = useState([]);
    const [category, setCategory] = useState([]);
    const decoded = jwtDecode(JSON.parse(localStorage.getItem("token")));


    useEffect(() => {
        let url = params.product_id ? 'http://localhost:8080/products/?category_id='+ params.product_id : 'http://localhost:8080/products';
        const getAllData =  async () => {
            const [category,products] = await getCategoryandProducts(url);
            setProducts(products)
            setCategory(category)
        }
        getAllData();
    }, [location]) 


    const handleSelect = (ev) => {
        navigate(ev.target.value);
    }

    return (
        <div className="container mt-5">
            <div className="row">
                <div className="col-md-6">
                    <h2>List of Category</h2>
                    <select onChange={handleSelect} className="form-select mb-3">
                        <option value='/products/'>All Products</option>
                        {category.map((cat) => (
                            <option key={cat.id} value={'/products/categories/' + cat.id}>
                                {cat.name}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="mt-3">
                {decoded.role === 'ROLE_ADMIN' &&
                <>
                <Link to="/category/create" className="btn btn-primary">Create New Category</Link>&nbsp;
                <Link to="/categories/" className="btn btn-primary">Show Categories</Link>
                </>
                }
                <hr/>
            </div>
            <div>
                    <h2>List of Products</h2>
                    <div className="list-group d-flex flex-row flex-wrap">
                        {products.map((prod) => (
                            <div key={prod.id} className="list-group-item border-0 row">
                                        <Link to={"/products/" + prod.id}><img src={`data:image/jpeg;base64,${prod.productImage}`} alt={prod.name} /></Link>
                                    <div>    
                                        <Link to={"/products/" + prod.id} className="text-decoration-none link-opacity-50-hover ">{prod.name}</Link> <br/>
                                        <p className="mb-0 oldprice d-inline"><del>{prod.price + 125} ₸</del></p>&nbsp;&nbsp;
                                        <strong><p className="mb-0 d-inline">{prod.price} ₸</p></strong>
                                    </div>
                            </div>
                        ))}
                    </div>
                </div>

            </div>
            <div className="mt-3">
            {decoded.role === 'ROLE_ADMIN' &&
                <Link to="/products/create" className="btn btn-primary">Create New Product</Link>
            }

            </div><br/>
        </div>
    );
}