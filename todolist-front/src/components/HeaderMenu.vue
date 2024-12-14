<script>

import {useUserStore} from "@/stores/user.ts";
const API_URL = import.meta.env.VITE_API_URL;


export default {
    data: () => ({
      connexion: false
    }),

    //When the page loads
    beforeMount() {
      const store = useUserStore()
      this.connexion = store.isAuthenticate() //Update if the user is connected or not
    },
  methods: {

      //Function to log out the user
      async deconnection() {
        const store = useUserStore()
        const result = await fetch(`http://${API_URL}/auth`, {
          method: "DELETE",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({"token": store.getToken()})
        })
        if(result.ok){
          store.deleteStorage() //Clear user data
          this.$router.push({name: 'home'}) //Redirection on the home page
        } else {
          alert("An error occurred, please try again later.")
        }
      }
  }
}
</script>

<template>
  <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <v-app-bar :elevation="0">
    <template v-slot:prepend>

      <!--  If the user is not connected    -->
      <i class="fa fa-home w3-xxlarge cursor-pointer" @click="$router.push({name: 'home'})" v-if="!connexion"></i>

      <!--  If the user is connected    -->
      <i class="fa fa-home w3-xxlarge cursor-pointer" @click="$router.push({name: 'todos'})" v-else></i>
    </template>

    <v-app-bar-title>Todo list application</v-app-bar-title>
    <v-spacer></v-spacer>

    <!--  Only if the user is connected  -->
    <v-icon icon="mdi-logout" size="xx-large" class="pa-5" v-if="connexion" @click="deconnection()"/>
  </v-app-bar>
</template>

<style scoped>

</style>