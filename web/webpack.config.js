module.exports = {
  entry: './src/index.js',
  mode: process.env['mode'] || 'development',
  module: {
    rules: [
      {
        test: /\.jsx?$/,
        exclude: /node_modules/,
        loader: 'babel-loader'
      }
    ]
  },
  resolve: {
    extensions: ['.js', '.jsx'],
  }
};
