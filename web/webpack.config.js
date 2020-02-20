const UglifyJSPlugin = require('uglifyjs-webpack-plugin')

const production = process.env['mode'] == 'production'

module.exports = {
  entry: './src/index.js',
  mode:  production ? 'production' : 'development',
  module: {
    rules: [
      {
        test: /\.jsx?$/,
        exclude: /node_modules/,
        loader: 'babel-loader'
      }
    ]
  },
  plugins:[
    new UglifyJSPlugin({
      sourceMap: !production,
      uglifyOptions: {
        output: {
          comments: !production
        }
      }
    })
  ],
  resolve: {
    extensions: ['.js', '.jsx'],
  }
};
