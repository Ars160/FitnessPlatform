import axios from "axios";

const authHeaders = () => {
    const token = JSON.parse(localStorage.getItem("token"));
    
    if(token && token.token)
    return {headers: {
        'Authorization': `Bearer ${JSON.parse(token)}`
    }}
    else{
        return {};
    }
};

export const registerUser = async (formData) => {
    const response = await axios.post("http://localhost:8080/auth/register", formData,);
    return response.data;
}

export const loginUser = async (formData) => {
    const response = await axios.post("http://localhost:8080/auth/login", formData);
    return response.data;
}

export const getMyUserId = async (email) => {
    const response = await axios.get("http://localhost:8080/clients/" + email + "/user", 
    { headers: authHeaders()}
    );
    return response.data;
}

// -------------------------------------------------------Products----------------------------------------------------------

export const getAllProducts = async () => {
    const response = await axios.get('http://localhost:8080/products');
    return response.data;
}

export const getCategoryandProducts = async (url) => {
    const [category,products] = await axios.all([
        axios.get('http://localhost:8080/category'),
        axios.get(url)
    ]);
    return [category.data, products.data];
}

export const getCategories = async () => {
    const response = await axios.get('http://localhost:8080/category')
    return response.data;
}

export const getOneCategory = async (id) => {
    const response = await axios.get('http://localhost:8080/category/'+id)
    return response.data;
}

export const addCategory = async (category) => {
    const token = localStorage.getItem('token');
    const response = await axios.post('http://localhost:8080/category/addCategory', category ,    
    );
    return response.data;
}

export const removeCategory = async(cat_id) => {

    const response = await axios.delete('http://localhost:8080/category/delete/'+cat_id)
    return response.data;
}

export const getOneProduct = async (product_id) => {
    const response = await axios.get('http://localhost:8080/products/'+product_id);
    return response.data;
}

export const addProduct = async (formDataToSend) => {
    const token = localStorage.getItem('token');
    const response = await axios.post('http://localhost:8080/products/addProduct', formDataToSend, 
    {headers: {
        'Authorization': `Bearer ${JSON.parse(token)}`
    }}    
    );
    return response.data;
}

export const updateProduct = async (product) => {
    const response = await axios.put('http://localhost:8080/products/update/'+product.get('id'), product);
    return response.data;
}

export const removeProduct = async (product_id) => {
    const response = await axios.delete('http://localhost:8080/products/delete/'+product_id);
    return response.data;
}

export const buyProduct = async (product_id) => {
    const token = localStorage.getItem('token');
    console.log(JSON.parse(localStorage.getItem("user")));
    const response = await axios.post('http://localhost:8080/products/'+product_id+'/buy',JSON.parse(localStorage.getItem("user")),
    {headers: {
        'Authorization': `Bearer ${JSON.parse(token)}`
    }},
    );
    return response.data;
}

export const SearchProduct = async (product_name) => {
    const response = await axios.get('http://localhost:8080/products/search/'+product_name);
    return response.data;
}