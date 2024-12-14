<script>
import PopupFormNewTodo from "@/components/PopupFormNewTodo.vue";
import {useUserStore} from "@/stores/user.ts";
const API_URL = import.meta.env.VITE_API_URL;

export default {
  components: {PopupFormNewTodo},
  data: () => ({
    todos: [],
  }),

  //When the page loads
  async beforeMount() {
    const store = useUserStore()
    if (!store.isAuthenticate()) {
      this.$router.push({name: 'home'}) //Redirection if the user is not connected
    } else {
      await this.getTodos()
    }
  },
  methods: {

    //Function to get the todo list of the user
    async getTodos() {
      const store = useUserStore()
      const result = await
      fetch(`http://${API_URL}/users/${store.getId()}/todos`, {
        method: "GET",
        headers: {"Authorization": `Token ${store.getToken()}`}
      })
      if (result.ok) {
        this.todos = await result.json()
      } else {
        alert("An error occurred while retrieving your data, please try again later.")
      }
    },

    //Function to delete a todo
    async deleteTodo(id) {
      const store = useUserStore()
      const result = await fetch(`http://${API_URL}/users/${store.getId()}/todos/${id}`, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Token ${store.getToken()}`
        }
      })
      if (result.ok) {
        await this.getTodos()
      } else {
        alert("An error occurred while retrieving your data, please try again later.")
      }
    },

    //Function to change the "done" property of a todo
    async changeTodo(item) {
      const store = useUserStore()
      const result = await fetch(`http://${API_URL}/users/${store.getId()}/todos/${item.id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Token ${store.getToken()}`
        },
        body: JSON.stringify({"value":item.value, "done": item.done})
      })
      if (result.ok) {
        await this.getTodos()
      } else {
        alert("An error occurred while retrieving your data, please try again later.")
      }
    }
  }
}
</script>

<template>
  <v-card
      class="mx-auto"
      width="50%"
      style="padding: 0.8%; display: flex; flex-direction: column; align-items: center"
  >
    <h1 style="width: 100%; text-align: center">Todos</h1>
    <v-container v-for="item in todos" style="padding: 0; display: flex; flex-direction: row; align-items: center; justify-content: space-between">

      <!--   To do not done   -->
      <v-checkbox v-model="item.done" :label="item.value" class="text-h5" style="color: black; font-weight: bold;" v-if="!item.done" @change="changeTodo(item)"></v-checkbox>

      <!--   To do done   -->
      <v-checkbox v-model="item.done" :label="item.value" class="text-h5" style="color: darkgray;" v-else @change="changeTodo(item)"></v-checkbox>

      <v-icon icon="mdi-delete" @click="deleteTodo(item.id)"></v-icon>
    </v-container>

    <popup-form-new-todo @new="getTodos"/>
  </v-card>
</template>
