<script>
  import {useUserStore} from "@/stores/user.ts";
  const API_URL = import.meta.env.VITE_API_URL;


  export default {
    data: () => ({
      items: [
        { text: 'Log in', icon: 'mdi-account-key', link: 'connection' },
        { text: 'Register', icon: 'mdi-account-plus', link: 'register' },
      ],
    }),

    //When the page loads
    beforeMount() {
      const store = useUserStore()
      if(store.isAuthenticate()){
        this.$router.push({name: 'todos'}) //Redirection if the user is connected
      }
    }
  }
</script>

<template>
  <v-card
      class="mx-auto"
      max-width="500"
  >
    <v-list>
      <span class="text-h4 ma-4" style="color: #5d5d5d">CONNECTION</span>

      <v-list-item
          v-for="(item, i) in items"
          :key="i"
          :value="item"
          color="primary"
          @click="$router.push({name: item.link})"
      >
        <template v-slot:prepend>
          <v-icon :icon="item.icon"></v-icon>
        </template>

        <v-list-item-title v-text="item.text" class="text-h5" style="padding: 2%"></v-list-item-title>
      </v-list-item>
    </v-list>
  </v-card>
</template>
