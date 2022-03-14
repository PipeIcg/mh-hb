const app = new Vue({
    el: '#app',
    data: {
        clients:[],
        transactionss:[],
        id:0,
        debitCard:[],
        creditCard:[],
        cards:[],
        debitTransaction: [],
        creditTransaction: [],
        account:0
    },
    created(){
        this.loadData()
        /* this.loadData2() */
        
    },
    methods:{
        loadData(){
            
            axios.get('/api/clients/current')
            .then(res => {
                this.clients = res.data
               this.cards = res.data.cards
               this.creditCard = this.cards.filter(card => card.type == 'CREDIT')
               this.debitCard = this.cards.filter(card => card.type == 'DEBIT')

               this.account = this.clients.accounts.length
               console.log(this.account)
               
              
                
               console.log(this.transactionss);
                
                console.log(this.clients)
                console.log(this.cards)
                
                
                
            })
            .catch(e => console.log(e))
        },
        logout(){
            axios.post('/api/logout')
            .then(response => {
                console.log('signed out!!!')
                return window.location.href = "/web/index.html"
            })
            .catch(e => console.log(e))
        },
        createAccount(){
            axios.post('/api/clients/current/accounts')
            .then(res => {
                return window.location.href = "/web/accounts.html"
            })
            .catch(e =>console.log(e))
        },

        transactions(account){
            window.location.href = "account.html?id=" + account.id
        }
    }
})

