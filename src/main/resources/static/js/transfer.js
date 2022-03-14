const app = new Vue({
    el: '#app',
    data: {
        clients: [],
        cards: [],
        creditCard: [],
        debitCard: [],
        amount: 0,
        description: '',
        originAccount: '',
        destinationAccount: ''
    },
    created() {
        this.loadData()
    },

    methods: {
        loadData() {
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
        makeTransaction() {
            
            axios.post('/api/transactions', `amount=${this.amount}&description=${this.description}&originAccount=${this.originAccount}&destinationAccount=${this.destinationAccount}`,
            {headers:{'content-type': 'application/x-www-form-urlencoded'}})
            .then(res =>{
                console.log("hola")
                return window.location.href = "/web/accounts.html"
            })
            .catch(e => console.log(e))
        }
        ,
        alert(){


            Swal.fire({
                title: 'confirm transfer?',
                showDenyButton: true,
                
                confirmButtonText: 'Confirm',
                denyButtonText: `Cancel`,
              }).then((result) => {

                /* Read more about isConfirmed, isDenied below */
                if (result.isConfirmed) {
                    
                    Swal.fire('Done!', '', 'success')
                    setTimeout(() => {
                        this.makeTransaction()
                    }, 2000);
                } else if (result.isDenied) {
                  Swal.fire('Changes are not saved', '', 'info')
                }
              })
        }
    }
})