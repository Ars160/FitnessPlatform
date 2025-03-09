import { Link, useLoaderData, useNavigate} from "react-router-dom";
import 'react-notifications/lib/notifications.css';
import { NotificationContainer, NotificationManager } from 'react-notifications';
import { removeCategory } from "../../api";

export default function Categories() {
    const navigate = useNavigate();
    const categories = useLoaderData();

    const handleClick = async (category) => {
        const confirmDelete = window.confirm('Are you sure you want to delete this entry?');
        if (confirmDelete) {
            const response = await removeCategory(category.id);
            navigate("/categories/", { state: { message: category.name + " deleted"} });
        }
    }

    return (
        <div className="container mt-5">
            <NotificationContainer />
            <div className="row">
                {categories.map((cat) => (
                    <div className="col-md-4 mb-3" key={cat.id}>
                        <div className="card">
                            <div className="card-body">
                                <h5 className="card-title">{cat.name}</h5>
                                <button onClick={() => handleClick(cat)} className="btn btn-danger me-2">Delete</button>
                            </div>
                        </div>
                    </div>
                ))}
                <Link to="/products/" className="btn btn-secondary">Back</Link>
            </div>
        </div>
    );
}
