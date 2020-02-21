import BlogList from 'BlogList'

function flushPromises() {
    return new Promise(resolve => setImmediate(resolve));
}

describe('BlogList', () => {
    it('display empty blogs', () => {
        let blogs = mount(<BlogList items={Promise.resolve({items: []})}/>);

        return flushPromises().then(() => {
            expect(blogs.find('#blogs')).toHaveDisplayName("ul");
            expect(blogs.find('#blogs').find("li.empty").exists()).toBe(false);
            blogs.update();

            expect(blogs.find('#blogs')).toHaveDisplayName("ul");
            expect(blogs.find('#blogs').find("li.empty").exists()).toBe(true);
            expect(blogs.find('#blogs').find("li.empty")).toHaveText("No blogs");
        });
    });
});