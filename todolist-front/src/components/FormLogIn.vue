<script>

import {useUserStore} from "@/stores/user.ts";

const API_URL = import.meta.env.VITE_API_URL;

const userStore = useUserStore()
export default {
  data: () => ({
    username: '',
    usernameRules: [
      value => {
        if (value?.length > 0) return true

        return 'Username is required.'
      },
    ],
    password: '',
    passwordRules: [
      value => {
        if (value?.length > 0) return true

        return 'Password is required.'
      },
    ],
    errorMessage: ''
  }),

  //When the page loads
  beforeMount() {
    const store = useUserStore()
    if(store.isAuthenticate()){
      this.$router.push({name: 'todos'}) //Redirection if the user is connected
    }
  },
  methods: {

    //Function to log in a user
    async submit(){
      if(this.login !== '' && this.password !== ''){

        const data = {
          login: this.username,
          password: this.password
        }

        //Request to log in the user
        const resultLogIn = await fetch(`http://${API_URL}/auth`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(data)
        })
        if(resultLogIn.ok){
          const datas = await resultLogIn.json()

          //Register data of the user in the local storage
          userStore.setToken(datas.token)
          userStore.setId(datas.id)

          this.$router.push({name: 'todos'}) //Redirection on the todo page
        } else {
          this.errorMessage = "Login or password incorrect."
        }
      }
    }
  }
}
</script>

<template>
  <v-sheet class="mx-auto w-25 pa-5" >
    <v-form @submit.prevent="submit" class="w-60">
      <v-container class="text-h4"><v-icon icon="mdi-account-key" class="text-h4 mr-3"/>LOG IN</v-container>
      <v-text-field
          v-model="username"
          :rules="usernameRules"
          label="Username"
          required
      ></v-text-field>

      <v-text-field
          type="password"
          v-model="password"
          :rules="passwordRules"
          label="Password"
          required
      ></v-text-field>

      <v-container class="text-red-darken-4 text-body-2 pa-0">{{ errorMessage }}</v-container>
      <v-btn class="mt-2" type="submit" block>Connection</v-btn>
    </v-form>
  </v-sheet>
</template>

<style scoped>
</style>