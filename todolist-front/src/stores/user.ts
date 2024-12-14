import {defineStore} from "pinia";

//Management of the session data
export const useUserStore = defineStore('user', () => {

    //Management of the token
    function setToken(token: String) {
        localStorage.setItem("tokenUser", token)
    }
    function getToken(){
        return localStorage.getItem("tokenUser")
    }

    //Management of the id
    function setId(id: String) {
        localStorage.setItem("idUser", id)
    }
    function getId(){
        return localStorage.getItem("idUser")
    }

    //Function to know if the user is logged in
    function isAuthenticate() {
        return getToken() != null && getId() != null;
    }

    //Function to clean user data when disconnecting
    function deleteStorage() {
        localStorage.clear()
    }

    return { setToken, setId, getToken, getId, isAuthenticate, deleteStorage }
})
