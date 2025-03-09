import { Link } from "react-router-dom";

export default function Page404(){
    return(
        <div>
            <p>404 There is no such page</p>
            <Link to="/">Main Page</Link>
        </div>
    );
}