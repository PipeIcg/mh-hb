const app = new Vue({
    el: '#app',
    data: {
        transactions: [],
        account:[],
        styleObject:{
            color: 'green'
        } 
    },
    created() {
        this.loadData()
    },
    methods: {
        loadData() {
            const urlParams = new URLSearchParams(window.location.search);
            const myParam = urlParams.get('id');
            axios.get(`/api/accounts/${myParam}`)
                .then(res => {
                    this.account = res.data
                    this.transactions = res.data.transactions
                    this.transactions.sort((a,b)=>{
                        return a.id - b.id
                    })
                
                    console.log(this.account)
                    console.log(this.transactions)
                    
                })
                .catch(e => console.log(e))
        }
        ,
        backToPage(){
            return window.location.href = "/web/accounts.html"
        }
    }
})
