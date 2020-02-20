import React from 'react'
import BlogList from 'BlogList'
import Enzyme, {shallow} from 'enzyme'
import Adapter from 'enzyme-adapter-react-16';
Enzyme.configure({adapter: new Adapter()});

describe('BlogList', () => {
    it('display empty blogs', function () {
        let blogs = shallow(<BlogList items={[]}/>);

        expect(blogs.find('#blogs').exists()).toBe(true)
        expect(blogs.find('#blogs').children().exists()).toBe(false)
    });
});