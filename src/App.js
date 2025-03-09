import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import Navigation from './Navigation';

import LoginPage from './components/ClientService/Login.Page';
import RegistrationPage from './components/ClientService/RegistrationPage';

import Products from './components/ProductService/Products';
import Page404 from './components/ProductService/Page404';
import ProductItem from './components/ProductService/ProductItem';
import CreateProduct from './components/ProductService/CreateProduct';
import EditProduct from './components/ProductService/EditProduct';
import Categories from './components/ProductService/Categories';
import CreateCategory from './components/ProductService/CreateCategory';

import { getAllProducts,getOneProduct,getCategories } from "./api";
export default function App(){
const routs = createBrowserRouter([
    {
        path: "/",
        element: <Navigation />,
        errorElement: <Page404 />,
        children: [
            {
                path: "products/",
                element: <Products />,
                loader: getAllProducts
            },
            {
                path: "products/categories/:product_id?",
                element: <Products />,
                loader: getAllProducts
            },
            {
                path: "products/:product_id",
                element: <ProductItem />,
                loader: async ({params}) => {
                    return getOneProduct(params.product_id)
                }
            },
            {
                path: "products/:product_id/edit",
                element: <EditProduct />,
                loader: getCategories
            },
            {
                path: "products/create",
                element: <CreateProduct />,
                loader: getCategories
            },
            {
                path: "category/create",
                element: <CreateCategory />
            },
            {
                path: "categories",
                element: <Categories />,
                loader: getCategories
            }
        ]
    },
    {
        path: "/login",
        element: <LoginPage />
      },
      {
        path: "/register",
        element: <RegistrationPage />
      }
]);

return(        
    <RouterProvider router={routs} />
)
}
