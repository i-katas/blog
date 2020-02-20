import BlogList from 'BlogList'

describe('BlogList', () => {
    it('display empty blogs', function () {
        let blogs = shallow(<BlogList items={[]}/>);

        expect(blogs.find('#blogs').exists()).toBe(true)
        expect(blogs.find('#blogs').children().exists()).toBe(false)
    });
});