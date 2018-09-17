const Path                        = require('path');
const WildcardsEntryWebpackPlugin = require('wildcards-entry-webpack-plugin');
const CopyWebpackPlugin           = require('copy-webpack-plugin');
const CleanWebpackPlugin          = require('clean-webpack-plugin')
const FromDirectory               = './source';
const ToDirectory                 = './webpack_output/59';

module.exports = {
    mode: 'development',
    entry: WildcardsEntryWebpackPlugin.entry(FromDirectory + '/**/*.js'),
    output: {
        path: Path.resolve(__dirname, ToDirectory),
        filename: '[name].js'
    },
    module: {
        rules: [{
            test: /\.js$/,
            loader: 'babel-loader',
            exclude: /(node_modules)/,
            options: {
                compact: true
            }
        }]
    },

    plugins: [
        new CopyWebpackPlugin([{
            context: FromDirectory,
            from: '**/*',
            ignore: ['*.js']
        }])
    ],

    resolve: {
        extensions: ['.js'],
        modules: [Path.resolve(__dirname, FromDirectory), 'node_modules']
    }
};