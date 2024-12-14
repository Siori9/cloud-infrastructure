<script>
import {useUserStore} from "@/stores/user.ts";
const API_URL = import.meta.env.VITE_API_URL;


const userStore = useUserStore()

export default {
  data: () => ({
    loginMessage: '',
    username: '',
    usernameRules: [
      value => {
        if (value?.length > 4) return true

        return 'Username needs to be at least 4 characters.'
      },
    ],
    password: '',
    passwordRules: [
      value => !!value || 'Please enter a password',
      (value) => !!value || 'Please type password.',
      (value) => (value && /\d/.test(value)) || 'At least one digit',
      (value) => (value && /[A-Z]{1}/.test(value)) || 'At least one capital latter',
      (value) => (value && /[^A-Za-z0-9]/.test(value)) || 'At least one special character',
      (value) => (value && value.length >= 6 ) || 'At least minimum 6 characters',
    ],
    passwordVerification: '',
  }),

  //When the page loads
  beforeMount() {
    const store = useUserStore()
    if(store.isAuthenticate()){
      this.$router.push({name: 'todos'}) //Redirection if the user is connected
    }
  },
  methods: {

    //Function to do the verification of the equality of the password and the password verification
    passwordVerificationRules(value) {
      if (value === this.password) return true;
      return 'Password verification needs to be identical to the password.';
    },

    //Function to register a new user
    async submit(){
      if(this.login !== '' && this.password !== ''){

        const data = {
          login: this.username,
          password: this.password
        }

        //Request to create the new account
        const result = await fetch(`http://${API_URL}/users`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(data)
        })

        if(result.status === 409){ //Error if the username is already used
          this.loginMessage = "This username is already used"
        } else if (result.ok){
          alert("Your account has been created !")

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
            this.$router.push({name: 'home'}) //Redirection on the home page
          }
        } else {
          alert("An error occurred, please try again later.")
        }
      }
    }
  }
}
</script>

<template>
  <v-sheet class="mx-auto w-25 pa-5" >
    <form @submit.prevent="submit">
      <v-container class="text-h4"><v-icon icon="mdi-account-plus" class="text-h4 mr-3"/>REGISTER</v-container>
      <v-text-field
          v-model="username"
          :rules="usernameRules"
          label="Username"
          required
      ></v-text-field>
      <v-container class="text-red-darken-4 text-body-2 pa-0">{{ loginMessage }}</v-container>

      <v-text-field
          v-model="password"
          :rules="passwordRules"
          label="Password"
          required
          type="password"
      ></v-text-field>

      <v-text-field
          v-model="passwordVerification"
          :rules="[ passwordVerificationRules ]"
          label="Confirm password"
          required
          type="password"
      ></v-text-field>

      <v-btn
          class="me-4"
          type="submit"
      >
        Register
      </v-btn>
    </form>
  </v-sheet>
</template>

<style scoped>

</style>