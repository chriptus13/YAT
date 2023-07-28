const CopyWebpackPlugin = require('copy-webpack-plugin')

module.exports = {
    mode: 'development',
    devServer: {
        contentBase: './dist',
        historyApiFallback: true
    },
    plugins: [
        new CopyWebpackPlugin([
            { from: './*.html' },
            { from: './favicon.ico' },
            { from: './YAT.gif' }
        ])
    ],
    module: {
        rules: [
            {
                test: /\.(png|woff|woff2|eot|ttf|svg)$/,
                use: {
                    loader: 'url-loader?limit=100000'
                }
            },
            {
                test: /\.(js)$/,
                exclude: /node_modules/,
                use: {
                    loader: 'babel-loader'
                }
            }, 
            {
                test: /\.css$/,
                use: [ 'style-loader', 'css-loader' ]
            }
        ]
    }
}