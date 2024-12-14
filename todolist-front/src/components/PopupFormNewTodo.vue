<script>
import {useUserStore} from "@/stores/user.ts";
const API_URL = import.meta.env.VITE_API_URL;


export default {
  data: () => ({
    dialog: false,
    todoValue: ""
  }),
  methods: {

    //Function to add a new todo
    async addTodo() {
      const store = useUserStore()
      const result = await fetch(`http://${API_URL}/users/${store.getId()}/todos`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Token ${store.getToken()}`
        },
        body: JSON.stringify({"value":this.todoValue})
      })
      if(result.ok) {
        this.dialog = false
        this.$emit("new") //To notify the addition of a todo
      } else {
        alert("An error occurred, please try again later.")
      }
    }
  }
}
</script>

<template>
  <v-btn icon="mdi-plus" variant="tonal" @click="dialog = true"></v-btn>
      <v-dialog
          v-model="dialog"
          max-width="600"
      >

        <v-card
            prepend-icon="mdi-note-plus"
            title="New to do"
        >
          <v-card-text>
                <v-textarea label="To do" variant="solo-filled" v-model="todoValue"></v-textarea>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>

            <v-btn
                text="Close"
                variant="plain"
                @click="dialog = false"
            ></v-btn>

            <v-btn
                color="primary"
                text="Save"
                variant="tonal"
                @click="addTodo()"
            ></v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
</template>